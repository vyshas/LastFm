package com.example.testlastfm.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import retrofit2.Response;


@RunWith(JUnit4.class)
public class ApiResponseTest {

    @Test
    public void success() {
        ApiResponse<String> apiResponse = new ApiResponse<>(Response.success("SuccessTest"));
        assertThat(apiResponse.errorMessage, nullValue());
        assertThat(apiResponse.code, is(200));
        assertThat(apiResponse.body, is("SuccessTest"));
        assertThat(apiResponse.getNextPage(), is(nullValue()));
    }

    @Test
    public void exception() {
        Exception exception = new Exception("Error");
        ApiResponse<String> apiResponse = new ApiResponse<>(exception);
        assertThat(apiResponse.links, notNullValue());
        assertThat(apiResponse.body, nullValue());
        assertThat(apiResponse.code, is(500));
        assertThat(apiResponse.errorMessage, is("Error"));
    }

}
