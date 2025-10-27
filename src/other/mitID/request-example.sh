curl -X 'POST' \
  'https://test-gateway.zignsec.com/api/v5/sessions/mitid/auth' \
  -H 'accept: application/json' \
  -H 'Authorization: Your API key' \
  -H 'Content-Type: application/json' \
  -d '{
    "locale": "en",
    "redirect_success": "https://my_success_url.com",
    "redirect_failure": "https://my_failure_url.com",
    "relay_state": "my-unique-customer-id",
    "gdpr_user_id": "my-unique-gdpr-customer-id",
    "webhook": "https://my_webhook_url.com",
    "metadata": {
        "popupContext": false,
        "method": "Loa",
        "mobile_info": {
            "device": "Ios",
            "return_app_url": "https://translate.google.com/?sl=da&tl=uk&text=It%27s%20done!&op=translate"
        },
        "level": "Low",
        "action": "LogOn",
        "referenceTextBody": "Log on to My Service",
        "serviceProviderReference": " My Service ",
        "requestedAttributes": [
            "DATE_OF_BIRTH",
            "AGE",
            "IAL_IDENTITY_ASSURANCE_LEVEL",
            "IDENTITY_NAME"
        ],
        "psd2": true
    }
}'

#        "method": "Loa",
#        "mobile_info": {
#            "device": "Ios",
#            "return_app_url": "https://translate.google.com/?sl=da&tl=uk&text=It%27s%20done!&op=translate"
#        },
