// 1 - Pacote
package petstore;

// 2 - Bibliotecas
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classe
public class Pet {
    // 3.1 - Atributos
    String uri =  "https://petstore.swagger.io/v2/pet";

    // 3.2 - Métodos e Funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    @Test(priority = 1)
    public void incluiPet() throws IOException {
        String jsonBody= lerJson("db/pet1.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Valente"))
                .body("status", is("available"))
                .body("category.name", is("dog"))
        ;

    }

    @Test(priority = 2)
    public void consultarPet(){
        String petId = "2612198712";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .statusCode(200)
                .log().all()
                .body("name", is("Valente"))
                .body("category.name", is("dog"))
        ;
    }

    @Test
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json")
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Valente"))
                .body("status", is("sold"))
        ;
    }

    @Test
    public void excluirPet(){
        String petId = "2612198712";

        given()
                .contentType("application/json")
                .log().all()
        .when()
                .delete(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", is(petId))
        ;
    }

}





















