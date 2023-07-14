package com.guiasexperts.uth.data.cotroller;

import java.io.IOException;

import com.guiasexperts.uth.data.entity.ResponseCustomer;
import com.guiasexperts.uth.data.entity.ResponsePaquetes;
import com.guiasexperts.uth.data.service.guiasexpertsRepositoryImpl;
import com.guiasexperts.uth.views.paqueteshn.paquetesViewModel;


	public class PaquetesInteractorImpl implements PaquetesInteractor {
		
	private guiasexpertsRepositoryImpl modelo;
    private paquetesViewModel vista;
	
	public PaquetesInteractorImpl (paquetesViewModel vista) {
		super ();
		this.modelo = guiasexpertsRepositoryImpl.getInstance("https://apex.oracle.com", 600000L);
	this.vista = vista;
	
	}

	
	
	


	   public void consultarPaquetes() {
		try {
			ResponsePaquetes response = this.modelo.getPaquetes();
			this.vista.refrescarGridPaquetes(response.getItems());
			}catch (IOException e) {
		e.printStackTrace();
		
		
			
		
		
	
	

	}

		}






	}


	


	
		
