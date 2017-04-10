package br.com.rbarbioni.rbstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by renan on 08/04/17.
 */
public class MoipPaymentRequest {

    private final Integer installmentCount;

    private final FundingInstrument fundingInstrument;

    @JsonCreator
    public MoipPaymentRequest(
            @JsonProperty Integer installmentCount,
            @JsonProperty FundingInstrument fundingInstrument) {
        this.installmentCount = installmentCount;
        this.fundingInstrument = fundingInstrument;
    }

    public Integer getInstallmentCount() {
        return installmentCount;
    }

    public FundingInstrument getFundingInstrument() {
        return fundingInstrument;
    }
}