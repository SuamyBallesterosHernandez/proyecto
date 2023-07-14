package com.guiasexperts.uth.data.cotroller;

import java.io.IOException;

import com.guiasexperts.uth.data.entity.ResponseCustomer;
import com.guiasexperts.uth.data.service.guiasexpertsRepositoryImpl;
import com.guiasexperts.uth.views.gestioncliente.customerViewModel;


public class CustomerInteractorImpl implements CustomerInteractor {
	private guiasexpertsRepositoryImpl modelo;
	private customerViewModel vista;

	
	public CustomerInteractorImpl (customerViewModel vista) {
		super ();
		this.modelo = guiasexpertsRepositoryImpl.getInstance("https://apex.oracle.com", 600000L);
	this.vista = vista;
	
	}

	
	
	
	@Override
	public void consultarClientes() {
		try {
			ResponseCustomer respuesta = this.modelo.getCustomer();
			this.vista.refrescarGridClientes(respuesta.getItems());
				
			

		}catch (IOException e) {
		e.printStackTrace();
		
		
			
		
		
	
	

	}

		}





	}


		
	


	
		
