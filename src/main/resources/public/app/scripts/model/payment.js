function Payment() {
    return{
        'installmentCount': 0,
        'statementDescriptor': '',
        'fundingInstrument': {
            'method': 'CREDIT_CARD',
            'creditCard': {
                'hash': '',
                'store': true,
                'holder': {
                    'fullname': 'Jose Portador da Silva',
                    'birthdate': '1988-12-30',
                    'taxDocument': {
                        'type': 'CPF',
                        'number': '33333333333'
                    },
                    'phone': {
                        'countryCode': '55',
                        'areaCode': '11',
                        'number': '66778899'
                    }
                }
            }
        }
    }
}