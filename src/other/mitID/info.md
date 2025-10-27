Source: https://docs.zignsec.com/eids/mitid-dk

Test Environment:
1.       The customer provides the necessary data to initiate MitID testing.
2.       ZignSec supplies a subscription key along with test link details in a secure format.
3.       The customer integrates and implements the MitID product.

Level of Assurance, Substantial:
  Most public services and online banking demand two forms of verification, such as the MitID app and PIN.

Web/Desktop:
  If accessed via a browser or desktop environment, the mobileInfo parameter should be omitted or set to null.

https://docs.zignsec.com/eids/mitid-dk/#URL+and+Page+Title+Guidelines+for+MitID+Integration
URL Requirements;
  Domain Association: MitID authentication must always connect to a URL under the MitID.dk domain.
  Broker's Role: The Broker must display a subdomain associated with either the Broker or the Service Provider.
  Service Provider Emphasis: It is strongly recommended to use the Service Provider's name for the subdomain. This approach fosters user trust, as end-users are more likely to recognize and associate with the Service Provider than the Broker.

Best Practices for URL Structure
  Security and Clarity: The URL should create a sense of security by clearly reflecting the Service Provider's identity. Avoid using the Broker's name, as most end-users are unfamiliar with their role.
  Name Consistency: The URL name should match the name displayed in the MitID box for continuity.
  Shortened Names: If the Service Provider's name is too long, use a shortened version in the URL, ensuring the MitID.dk domain remains visible for user confidence.

Page Title Recommendation
  The page title for MitID integration should simply be "MitID" to align with the platform's branding and ensure clarity for users.

Please:
  Update the Reference Text Header (including action text and Service Provider name)
  Upload the Service Provider Logo

Update the Reference Text Body.
The reference text body is a descriptive text, up to 130 characters, shown during the final step of the MitID box. In the MitID app, it is displayed only within the app interface.
Recommendations for Updating the Reference Text Body
    Clarity: Provide a clear, detailed description of what the user is approving.
    Context: Help users understand the implications of the approval beyond authentication.
    Avoid Repetition: Do not repeat the text from the reference text header; provide additional insights.
    Consistency: Ensure alignment with the Service Provider's system and the MitID box.
    Character Limit: Keep the text within 130 characters (maximum 3 lines) to avoid truncation.
    Alignment: Ensure consistency between the Service Provider’s branding and the reference text to prevent confusion.
    Mandatory Text: Always include a reference text body; avoid leaving it blank.

Update the Reference Text Header.
The reference text header consists of an **action text** and the **Service Provider name**.
Recommendations:
  **Recognizable Name:** Use the exact Service Provider name that users are familiar with.
  **Avoid Unnecessary Details:** Exclude suffixes like “ApS” or “A/S” to simplify the name.
  **Avoid Broker Names:** Do not use broker names in place of the Service Provider name.
  **Predefined Action Texts:** Select from one of five predefined action texts that best fit the approval type.
  **Keep It Short:** Limit the header to two lines to avoid scrolling.
  **Character Limit:** For names longer than 32 characters, ensure proper line breaks.
  **Enhance Trust:** Align the name with what users expect to build clarity and confidence.
Format is `{Action} {Service Provider}`.
The 5 options for the action text:
|Danish|English|Greenlandic|
|---|---|---|
|Log på hos|Log on at|Uunga iserit|
|Godkend hos|Approve at|Uani akuersigit|
|Bekræft hos|Confirm at|Uani uppernarsaagit|
|Accepter hos|Accept at|Uani akuersigit|
|Underskriv hos|Sign at|Uani atsiorit|

Handling Errors.
If an error occurs during an ongoing authentication or signing session, the following scenarios may take place:
  The end-user might be shown an error message, often accompanied by an error code, with options to retry or return to the merchant application.
  If the error occurs under the "right conditions," the user can be redirected back to the merchant application, using the same parameters as when a user cancels the session.
  In rare cases, an error other than access_denied might be returned, along with an error_description parameter, depending on the situation.
  In severe cases, the user might be stuck on the MitID platform with no option to return to the merchant application.

# INCOMPLETE
