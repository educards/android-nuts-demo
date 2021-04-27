package com.educards.nuts.demo.server;

import com.educards.nuts.demo.dto.DemoLoginResponseDTO;
import com.educards.nuts.demo.dto.DemoSampleDataDTO;
import com.educards.nuts.retrofit2.Secured;
import com.educards.nuts.retrofit2.TemplateCall;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DemoService {

    @POST("v1/demo_login")
    @FormUrlEncoded
    TemplateCall<DemoLoginResponseDTO> demoLogin(@Field("j_username") String loginUser, @Field("j_password") String loginPassword);

    @GET("v1/demo_sample_data")
    @Secured
    TemplateCall<DemoSampleDataDTO> getDemoSampleData();

}
