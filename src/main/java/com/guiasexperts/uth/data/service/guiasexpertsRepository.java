package com.guiasexperts.uth.data.service;


import com.guiasexperts.uth.data.entity.ResponseCustomer;
import com.guiasexperts.uth.data.entity.ResponsePaquetes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface guiasexpertsRepository {
	
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset:utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	
	@GET("/pls/apex/jorgefernandez_pv2_2023/guiasexperts/clientes/")
	Call<ResponseCustomer> obtenerClientes();

	
	@Headers({
	    "Content-Type: application/json",
	    "Accept-Charset:utf-8",
	    "User-Agent: Retrofit-Sample-App"
	})
	
	@GET("/pls/apex/jorgefernandez_pv2_2023/guiasexperts/paquetes/")
	Call<ResponsePaquetes> obtenerPaquetes();


}
