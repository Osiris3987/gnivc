package com.example.portal_service.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CredentialPair<T, E> {
    private T firstElement;
    private E secondElement;
}
