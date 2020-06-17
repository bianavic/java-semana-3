package com.challenge.desafio;

import com.challenge.annotation.Somar;
import com.challenge.annotation.Subtrair;
import com.challenge.interfaces.Calculavel;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Arrays;

public class CalculadorDeClasses implements Calculavel {

    private BigDecimal calcularClasses(Object o, Class<? extends Annotation> annotation) {
        return Arrays.asList(fields(o))
                .stream()
                .filter(field -> field.isAnnotationPresent(annotation))
                .map(field -> filtrar(field, o))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Field[] fields(Object o) {
        return o.getClass().getDeclaredFields();
    }

    private BigDecimal filtrar(Field field, Object o) {
        try{
            field.setAccessible(true);
            return (BigDecimal) field.get(o);
        } catch (IllegalAccessException e) {
            return BigDecimal.ZERO;
        }
    }


    @Override
    public BigDecimal somar(Object o) {
        return calcularClasses(o, Somar.class);
    }

    @Override
    public BigDecimal subtrair(Object o) {
        return calcularClasses(o, Subtrair.class);
    }

    @Override
    public BigDecimal totalizar(Object o) {
        return somar(o).subtract(subtrair(o));
    }
}
