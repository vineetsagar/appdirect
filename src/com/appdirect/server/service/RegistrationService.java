package com.appdirect.server.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageExtension;
import org.openid4java.message.ParameterList;
import org.openid4java.message.sreg.SRegMessage;
import org.openid4java.message.sreg.SRegRequest;
import org.openid4java.message.sreg.SRegResponse;

import com.appdirect.server.conf.ConfLoader;

/**
 * Consolidates business logic 
 *
 */
public class RegistrationService {
	private static final Logger log = Logger.getLogger(RegistrationService.class);
	
	/**
	 * Perform discovery on the User-Supplied identifier and return the
	 * DiscoveryInformation object that results from Association with the
	 * OP. This will probably be needed by the caller (stored in Session
	 * perhaps?).
	 * 
	 * @param userSuppliedIdentifier The User-Supplied identifier. It may already
	 *  be normalized.
	 *
	 *  @return DiscoveryInformation - The resulting DisoveryInformation object
	 *  returned by openid4java following successful association with the OP.
	 */
	@SuppressWarnings("unchecked")
	public static DiscoveryInformation performDiscoveryOnUserSuppliedIdentifier(String userSuppliedIdentifier) {
		DiscoveryInformation ret = null;
		//
		ConsumerManager consumerManager = getConsumerManager();
		try {
		// Perform discover on the User-Supplied Identifier
		List<DiscoveryInformation> discoveries = consumerManager.discover(userSuppliedIdentifier);
		// Pass the discoveries to the associate() method...
		ret = consumerManager.associate(discoveries);

		} catch (DiscoveryException e) {
			String message = "Error occurred during discovery!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return ret;
	}
	/**
	 * Create an OpenID Auth Request, using the DiscoveryInformation object
	 * return by the openid4java library.
	 * 
	 * @param discoveryInformation The DiscoveryInformation that should have
	 *  been previously obtained from a call to 
	 *  performDiscoveryOnUserSuppliedIdentifier().
	 *  
	 *  @param returnToUrl The URL to which the OP will redirect once the
	 *   authentication call is complete.
	 *  
	 * @return AuthRequest - A "good-to-go" AuthRequest object packed with all
	 *  kinds of great OpenID goodies for the OpenID Provider (OP). The caller
	 *  must take this object and forward it on to the OP. Or call
	 *  processAuthRequest() - part of this Service Class.
	 */
	public static AuthRequest createOpenIdAuthRequest(DiscoveryInformation discoveryInformation, String returnToUrl) {
		AuthRequest ret = null;
		//
		try {
			// Create the AuthRequest object
			ret = getConsumerManager().authenticate(discoveryInformation, returnToUrl);
			// Create the Simple Registration Request
			SRegRequest sRegRequest = SRegRequest.createFetchRequest();
			// fetching only email and fullname from OP
			sRegRequest.addAttribute("email", false);
			sRegRequest.addAttribute("fullname", false);
			ret.addExtension(sRegRequest);
			
		} catch (Exception e) {
			String message = "Exception occurred while building AuthRequest object!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return ret;
	}
	
	/**
	 * Processes the returned information from an authentication request
	 * from the OP.
	 * 
	 * @param discoveryInformation DiscoveryInformation that was created earlier
	 *  in the conversation (by openid4java). This will need to be verified with
	 *  openid4java to make sure everything went smoothly and there are no
	 *  possible problems. This object was probably stored in session and retrieved
	 *  for use in calling this method.
	 *  
	 * @param pageParameters PageParameters passed to the page handling the
	 *  return verification.
	 *  
	 * @param returnToUrl The "return to" URL that was passed to the OP. It must
	 *  match exactly, or openid4java will issue a verification failed message
	 *  in the logs.
	 *  
	 * @return RegistrationModel - null if there was a problem, or a RegistrationModel
	 *  object, with parameters filled in as completely as possible from the
	 *  information available from the OP. 
	 */
	public static RegistrationModel processReturn(DiscoveryInformation discoveryInformation, ParameterList parameterList, String returnToUrl) {
		RegistrationModel ret = null;
		// Verify the Information returned from the OP
		/// This is required according to the spec
		try {
			VerificationResult verificationResult = getConsumerManager().verify(returnToUrl, parameterList, discoveryInformation);
			Identifier verifiedIdentifier = verificationResult.getVerifiedId();
			if (verifiedIdentifier != null) {
				AuthSuccess authSuccess = (AuthSuccess)verificationResult.getAuthResponse();
				if (authSuccess.hasExtension(SRegMessage.OPENID_NS_SREG)) {
					MessageExtension extension = authSuccess.getExtension(SRegMessage.OPENID_NS_SREG);
					if (extension instanceof SRegResponse) {
						ret = new RegistrationModel();
						ret.setOpenId(verifiedIdentifier.getIdentifier());
						SRegResponse sRegResponse = (SRegResponse)extension;
						List attributeNames = sRegResponse.getAttributeNames();
						if(attributeNames!=null){
							System.out.println("attributes " + attributeNames);
						}
						String value = sRegResponse.getAttributeValue("email");
						if (value != null) {
						  ret.setEmail(value);
						}
						value = sRegResponse.getAttributeValue("fullname");
						if (value != null) {
						  ret.setFullName(value);
						}
					}
				}
			}
		} catch (Exception e) {
			String message = "Exception occurred while verifying response!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return ret;
	}

	private static ConsumerManager consumerManager;
	/**
	 * Retrieves an instance of the ConsumerManager object. It is static
	 * (see note in Class-level JavaDoc comments above) because openid4java
	 * likes it that way.
	 * 
	 * Note: if you are planning to debug the code, set the lifespan parameter
	 * of the InMemoryNonceVerifier high enough to outlive your debug cycle, or
	 * you may notice Nonce Verification errors. Depending on where you are
	 * debugging, this might pose an artificial problem for you (of your own
	 * making) that has nothing to do with either your code or openid4java.
	 * 
	 * @return ConsumerManager - The ConsumerManager object that handles
	 *  communication with the openid4java API.
	 */
	private static ConsumerManager getConsumerManager() {
		try {
			if (consumerManager == null) {
				consumerManager = new ConsumerManager();
				consumerManager.setAssociations(new InMemoryConsumerAssociationStore());
				consumerManager.setNonceVerifier(new InMemoryNonceVerifier(10000));
			}
		} catch (ConsumerException e) {
			String message = "Exception creating ConsumerManager!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return consumerManager;
	}
	
  /**
   * Generates the returnToUrl parameter that is passed to the OP. The
   * User Agent (i.e., the browser) will be directed to this page following
   * authentication.
   * @return String - the returnToUrl to be used for the authentication request.
   */
  public static String getReturnToUrl() {
	  return ConfLoader.getLoginSuccessURL();
  }
}
