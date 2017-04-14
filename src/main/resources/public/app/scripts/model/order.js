function Order() {

    var order =
        {
            ownId: '',
            amount: {
                currency: 'BRL',
                subtotals: {
                    shipping: 0,
                    addition: 0,
                    discount: 0
                }
            },
            items: [],
            customer: {}
        };
    return order;
}