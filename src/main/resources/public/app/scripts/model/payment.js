function Payment() {
    return{
        'installmentCount': 0,
        'statementDescriptor': '',
        'fundingInstrument': {
            'method': 'CREDIT_CARD',
            'creditCard': {
                'hash': '',
                'store': true,
                'holder': {}
            }
        }
    }
}