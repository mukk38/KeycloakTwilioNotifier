# Keycloak Event Listener with Twilio SMS Notifications

## Description
This project is a **Spring Boot-based application** that listens to **Keycloak admin events** and sends **SMS notifications** via **Twilio API**. It allows you to monitor user actions and other admin events in Keycloak and alert administrators or users through SMS or WhatsApp messages.

### Key Features:
- Listens to **Keycloak admin events** 🛡️
- Sends **SMS notifications** using the **Twilio API** 📩
- Built with **Spring Boot**, **Keycloak Admin Client**, and **Twilio SDK**

## Requirements
1. **Keycloak** instance running on your server.
2. **Twilio** account for SMS and WhatsApp messaging.
3. **Spring Boot** environment.

## How It Works
This application listens for **Keycloak admin events** (e.g., user creation, deletion, role updates, etc.) and triggers **Twilio API** to send an SMS or WhatsApp message whenever a specified event occurs.

## Configuration

You need to configure the following properties for both **Keycloak** and **Twilio** to make the application work.

### KEYCLOAK

```properties
keycloak.client.realm=MUSTAFA
keycloak.server.url=http://192.168.1.52/auth
keycloak.client.master=master
keycloak.client.id=admin-cli
keycloak.user.name=admin
keycloak.user.password=password
keycloak.client.name=MKA

```

### KEYCLOAK Configuration

Configure the following properties in the `application.properties` file to connect your application with Keycloak:

- **keycloak.client.realm**: The realm you're using in Keycloak (e.g., `MUSTAFA`).
- **keycloak.server.url**: The URL of your Keycloak server. Make sure the application can access this URL (e.g., `http://192.168.1.52/auth`).
- **keycloak.client.master**: The master realm name (default is `master`).
- **keycloak.client.id**: The client ID for your Keycloak application (e.g., `admin-cli`).
- **keycloak.user.name**: The admin username to authenticate with Keycloak (e.g., `admin`).
- **keycloak.user.password**: The admin password for Keycloak (e.g., `password`).
- **keycloak.client.name**: The name of the Keycloak client you're using (e.g., `MKA`).

### TWILIO

```properties
twilio.accountSid=YOUR_ACCOUNT_SID
twilio.authToken=YOUR_AUTH_TOKEN
twilio.phoneNumber=YOUR_TWILIO_PHONE_NUMBER
twilio.whatsAppNumber=+YOUR_WHATSAPP_TWILIO_PHONE_NUMBER

```

### Twilio Configuration

Configure the following properties in the `application.properties` file to connect your application with Keycloak:

- **twilio.accountSid**: Your Twilio Account SID.
- **twilio.authToken:**: Your Twilio Auth Token.
- **twilio.phoneNumber:**: Your Twilio phone number for SMS.
- **twilio.whatsAppNumber**: Your WhatsApp number for sending WhatsApp messages (formatted as +1XXXXXXXXXX).

### How to Run

1.  Clone this repository to your local machine:
```
git clone https://github.com/your-username/keycloak-twilio-sms-notifier.git
cd keycloak-twilio-sms-notifier
```
2. Update the application.properties file with your Keycloak and Twilio credentials.
3. Build and run the application:
```
./mvnw spring-boot:run
```
4. The application will start listening to Keycloak admin events and trigger Twilio SMS or WhatsApp notifications accordingly.

### Example API Calls

You can manually trigger an event by interacting with Keycloak through the Admin Console or by using the Keycloak API.

### Keycloak Configuration

To enable admin event notifications and include the `phoneNumber` attribute for new users, follow these steps:

1. **Enable Admin Events**:
    - Navigate to **Realm Settings** in the Keycloak admin console.
    - Go to the **Events** tab.
    - Under **Admin Event Settings**, enable the relevant settings for admin events.

2. **Add `phoneNumber` Attribute**:
    - When creating a new user, ensure that the `phoneNumber` attribute is added to the **Attribute Map** field.

This setup is necessary for integrating with the Twilio API to send SMS/WhatsApp notifications when certain admin events occur.
