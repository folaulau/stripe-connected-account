package com.folautech.springbootapi.stripe;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class PaymentIntentDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            accountId;

    private String            clientSecret;

    private String            id;

    private Double            amount;

}
