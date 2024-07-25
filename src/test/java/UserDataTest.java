import static constatns.Urls.*;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import service.Specification;
import pojo.UserData;

public class UserDataTest {



    @Test
    public void checkAvatarAndIdTest(){
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
        List<UserData> users  = given()
                .when()
                .get(USER_LIST_URL + "2")
                .then().log().all()
                .extract().body().jsonPath().getList("data", UserData.class);

        users.forEach(x -> Assert.assertTrue(x.getAvatar().contains(x.getId().toString())));
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("reqres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).toList();
        List<String> ids = users.stream().map(x -> x.getId().toString()).toList();

        for (int i = 0; i < avatars.size(); i++) {
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }

    }

    @Test
    public void checkSingleUserData(){
        Integer userID = 2;
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
        UserData user = given()
                .when()
                .get(USER_URL + userID)
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);

        Assert.assertEquals(user.getId(), userID);
        Assert.assertTrue(user.getEmail().endsWith("reqres.in"));
        Assert.assertTrue(user.getAvatar().contains(user.getId().toString()));
    }

    @Test
    public void checkInvalidSingleUserData(){
        Integer userID = 23;
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpec404NotFound());
        UserData user = given()
                .when()
                .get(USER_URL + userID)
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);

    }

    @Test
    public void deleteUser(){
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecUnique(204));
        given()
                .when()
                .delete(USER_URL + 2)
                .then().log().all();


    }

    @Test
    public void userJobUpdate(){
        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());
        UserData userJob = new UserData("morpheus", "zion resident");
        UserData response = given()
                .when()
                .body(userJob)
                .put(USER_URL + 2)
                .then().log().all()
                .extract().as(UserData.class);
        System.out.println(response.getUpdatedAt().toString());

        UserData user = given()
                .when()
                .get(USER_URL + 2)
                .then().log().all()
                .extract().body().jsonPath().getObject("data", UserData.class);

        Assert.assertEquals(response.getName(), user.getFirst_name());
    }

}
