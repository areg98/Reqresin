import static constatns.Urls.BASE_URL;
import static constatns.Urls.LOGIN_URL;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import pojo.login.Login;
import pojo.login.SuccessLogin;
import pojo.login.UnSuccessLogin;
import service.Specification;

public class LoginTest {

    @Test
    public void successLogin(){
        Login user = new Login("eve.holt@reqres.in", "cityslicka");

        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
        SuccessLogin successLogin = given()
                .body(user)
                .when()
                .post(LOGIN_URL)
                .then().log().all()
                .extract().as(SuccessLogin.class);

        Assert.assertNotNull(successLogin.getToken());
    }

    @Test
    public void unSuccessLogin(){
        Login user = new Login("eve.holt@reqres.in", "");

        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpec400());
        UnSuccessLogin unSuccessLogin = given()
                .body(user)
                .when()
                .post(LOGIN_URL)
                .then().log().all()
                .extract().as(UnSuccessLogin.class);

        Assert.assertNotNull(unSuccessLogin.getError(), "Missing password");
    }


}
