/*
SPDX-License-Identifier: Apache-2.0
*/

package hopsital.example;

import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Properties;
import java.util.Set;

import hopsital.hyperledger.fabric.gateway.Wallet;
import hopsital.hyperledger.fabric.gateway.Wallet.Identity;
import hopsital.hyperledger.fabric.sdk.Enrollment;
import hopsital.hyperledger.fabric.sdk.User;
import hopsital.hyperledger.fabric.sdk.security.CryptoSuite;
import hopsital.hyperledger.fabric.sdk.security.CryptoSuiteFactory;
import hopsital.hyperledger.fabric_ca.sdk.HFCAClient;
import hopsital.hyperledger.fabric_ca.sdk.RegistrationRequest;

public class RegisterUser {

	static {
		System.setProperty("hopsital.hyperledger.fabric.sdk.service_discovery.as_localhost", "true");
	}

	public static void main(String[] args) throws Exception {

		// Create a CA client for interacting with the CA.
		Properties props = new Properties();
		props.put("pemFile",
			"../../hospital-network/organizations/peerOrganizations/hospital1.geakminds.com/ca/ca.hospital1.geakminds.com-cert.pem");
		props.put("allowAllHostNames", "true");
		HFCAClient caClient = HFCAClient.createNewInstance("https://localhost:7054", props);
		CryptoSuite cryptoSuite = CryptoSuiteFactory.getDefault().getCryptoSuite();
		caClient.setCryptoSuite(cryptoSuite);

		// Create a wallet for managing identities
		Wallet wallet = Wallet.createFileSystemWallet(Paths.get("wallet"));

		// Check to see if we've already enrolled the user.
		boolean userExists = wallet.exists("appUser");
		if (userExists) {
			System.out.println("An identity for the user \"appUser\" already exists in the wallet");
			return;
		}

		userExists = wallet.exists("admin");
		if (!userExists) {
			System.out.println("\"admin\" needs to be enrolled and added to the wallet first");
			return;
		}

		Identity adminIdentity = wallet.get("admin");
		User admin = new User() {

			@Override
			public String getName() {
				return "admin";
			}

			@Override
			public Set<String> getRoles() {
				return null;
			}

			@Override
			public String getAccount() {
				return null;
			}

			@Override
			public String getAffiliation() {
				return "hospital1.department1";
			}

			@Override
			public Enrollment getEnrollment() {
				return new Enrollment() {

					@Override
					public PrivateKey getKey() {
						return adminIdentity.getPrivateKey();
					}

					@Override
					public String getCert() {
						return adminIdentity.getCertificate();
					}
				};
			}

			@Override
			public String getMspId() {
				return "Hospital1MSP";
			}

		};

		// Register the user, enroll the user, and import the new identity into the wallet.
		RegistrationRequest registrationRequest = new RegistrationRequest("appUser");
		registrationRequest.setAffiliation("hospital1.department1");
		registrationRequest.setEnrollmentID("appUser");
		String enrollmentSecret = caClient.register(registrationRequest, admin);
		Enrollment enrollment = caClient.enroll("appUser", enrollmentSecret);
		Identity user = Identity.createIdentity("Hospital1MSP", enrollment.getCert(), enrollment.getKey());
		wallet.put("appUser", user);
		System.out.println("Successfully enrolled user \"appUser\" and imported it into the wallet");
	}

}
