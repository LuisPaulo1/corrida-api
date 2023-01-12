package com.corrida.service.expection;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadFileException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;	

	public ReadFileException(String msg) {
		super(msg);
	}
}