package com.stellar.burgers.api;

import com.stellar.burgers.models.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserApiClient {
    private static final String BASE_URL = "https://stellarburgers.education-services.ru";
    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String DELETE_ENDPOINT = "/api/auth/user";

    public UserApiClient() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создание пользователя через API")
    public Response createUser(User user) {
        System.out.println("📝 Создаем пользователя через API: " + user.getEmail() + 
                         " с паролем: " + user.getPassword());
        
        Response response = given()
                .header("Content-type", "application/json")
                .body(user)
                .post(REGISTER_ENDPOINT);
        
        System.out.println("📥 Ответ от API: " + response.getStatusCode());
        return response;
    }

    @Step("Удаление пользователя через API")
    public void deleteUser(String accessToken) {
        if (accessToken != null && !accessToken.isEmpty()) {
            Response response = given()
                    .header("Authorization", accessToken)
                    .delete(DELETE_ENDPOINT);
            
            if (response.statusCode() == 202) {
                System.out.println("✅ Пользователь удален");
            }
        }
    }
}
