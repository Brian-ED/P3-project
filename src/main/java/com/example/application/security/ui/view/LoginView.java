package com.example.application.security.ui.view;

import com.example.database.PostgreSQLDatabaseControler;
import com.example.model.Citizen;
import com.example.model.DatabaseControler;
import com.example.model.Model;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.login.AbstractLogin.LoginEvent;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;

class ModelSetup implements ComponentEventListener<LoginEvent> {

	@Override
	public void onComponentEvent(LoginEvent event) {
        DatabaseControler db = new PostgreSQLDatabaseControler();

        if (event.getUsername() == "citizen") {
            String temporaryCitizenName = "Josh Isenhower";
            Citizen citizen = new Citizen(temporaryCitizenName);
			Model.InitModel(db, citizen);
        }
	}
}

@Route(value = "login", autoLayout = false)
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends Main implements BeforeEnterObserver {

    private final LoginForm login;

    public LoginView() {
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.JustifyContent.CENTER,
            LumoUtility.AlignItems.CENTER);
        setSizeFull();
        login = new LoginForm();
        login.setAction("login");
        ModelSetup modelSetup = new ModelSetup();
        login.addLoginListener(modelSetup);
        add(login);
    }

    // Vaadin docs https://vaadin.com/docs/latest/building-apps/security/add-login/flow
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation()
                 .getQueryParameters()
                 .getParameters()
                 .containsKey("error")) {
            login.setError(true);
        }
    }
}
