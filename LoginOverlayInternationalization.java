package com.example.app_name_here.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class LoginOverlayInternationalization extends VerticalLayout {

    public LoginOverlayInternationalization() {


      LoginI18n i18n = LoginI18n.createDefault();

      LoginI18n.Header i18nHeader = new LoginI18n.Header();
      i18nHeader.setTitle("Sovelluksen nimi");
      i18nHeader.setDescription("Sovelluksen kuvaus");
      i18n.setHeader(i18nHeader);

      LoginI18n.Form i18nForm = i18n.getForm();
      i18nForm.setTitle("Kirjaudu sisään");
      i18nForm.setUsername("Käyttäjänimi");
      i18nForm.setPassword("Salasana");
      i18nForm.setSubmit("Kirjaudu sisään");
      i18nForm.setForgotPassword("Unohtuiko salasana?");
      i18n.setForm(i18nForm);

      LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
      i18nErrorMessage.setTitle("Väärä käyttäjätunnus tai salasana");
      i18nErrorMessage.setMessage(
              "Tarkista että käyttäjätunnus ja salasana ovat oikein ja yritä uudestaan.");
      i18n.setErrorMessage(i18nErrorMessage);

      i18n.setAdditionalInformation("Jos tarvitset lisätietoja käyttäjälle.");

      LoginOverlay loginOverlay = new LoginOverlay();
      loginOverlay.setI18n(i18n);


      add(loginOverlay);
    }
}
