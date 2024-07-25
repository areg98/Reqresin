import static constatns.Urls.BASE_URL;
import static constatns.Urls.REGISTER_URL;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import pojo.Register;
import service.Specification;

public class RegisterTest {

    @Test
    public void successRegister(){
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";
        Register user = new Register("eve.holt@reqres.in", "pistol");
        Register successReg = given()
                .body(user)
                .when()
                .post(REGISTER_URL)
                .then().log().all()
                .extract().as(Register.class);


        Assert.assertNotNull(successReg.getId());
        Assert.assertNotNull(successReg.getToken());
        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }

    @Test
    public void unSuccessRegister(){

        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpec400());
        Register user = new Register("eve.holt@reqres.in", "");

        Register unSuccessReg = given()
                .body(user)
                .when()
                .post(REGISTER_URL)
                .then().log().all()
                .extract().as(Register.class);

        Assert.assertEquals(unSuccessReg.getError(), "Missing password");

    }


}
