package br.edu.univas.test.brendon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private long code;
	private String cpf;
	private String name;
	private String birthdate;
	private String gender;
	private String email;
	private String loyaltyrate;
	private boolean active;
}
