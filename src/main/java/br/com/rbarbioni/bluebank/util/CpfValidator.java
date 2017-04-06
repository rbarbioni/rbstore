package br.com.rbarbioni.bluebank.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by renan on 11/02/17.
 */
public class CpfValidator implements ConstraintValidator<Cpf, String> {

    @Override
    public void initialize(final Cpf constraintAnnotation) {}

    @Override
    public boolean isValid(String cpf, final ConstraintValidatorContext context) {
        boolean result;
        if ( cpf == null || "".equals(cpf) ) {
            result = false;
        } else {
            result = isCpf(cpf);
        }
        return result;
    }

    private boolean isCpf(String cpf) {
        cpf = cpf.replace(".", "");
        cpf = cpf.replace("-", "");

        try{
            Long.parseLong(cpf);
        } catch(NumberFormatException e){
            return false;
        }

        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;

        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;

        for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(cpf.substring(nCount - 1, nCount)).intValue();

            d1 = d1 + (11 - nCount) * digitoCPF;

            d2 = d2 + (12 - nCount) * digitoCPF;
        }

        resto = (d1 % 11);

        digito1 = 11 - resto;

        d2 += 2 * digito1;

        resto = (d2 % 11);

        digito2 = 11 - resto;

        String nDigVerific = cpf.substring(cpf.length() - 2, cpf.length());

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);

        return nDigVerific.equals(nDigResult);
    }

}