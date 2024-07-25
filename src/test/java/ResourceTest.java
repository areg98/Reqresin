import static constatns.Urls.BASE_URL;
import static constatns.Urls.RESURCES_LIST_URL;
import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import pojo.Resource;
import service.Specification;

public class ResourceTest {

    @Test
    public void checkResourceData(){

        Specification.installSpecification(Specification.requestSpec(BASE_URL), Specification.responseSpecOK200());

        List<Resource> resources = given()
                .when()
                .get(RESURCES_LIST_URL)
                .then().log().all()
                .extract().body().jsonPath().getList("data", Resource.class);

        List<Integer> years = resources.stream().map(Resource::getYear).toList();
        List<Integer> sortedYears = resources.stream().map(Resource::getYear).sorted().toList();

        System.out.println(resources);

        Assert.assertEquals(sortedYears, years);

    }
}
