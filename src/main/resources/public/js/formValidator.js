define(["jquery","vue","ajax","swal","translator","datetimepicker","bootstrapValidator"],function($, Vue, ajax, swal, translator, datetimepicker, bootstrapValidator){

    function validator(paramter){
        $("#" + paramter).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                number: {
                    validators: {
                        notEmpty: {
                            message: "number is required."
                        }
                    }

                },
                alias: {
                    validators: {
                        notEmpty: {
                            message: "elevator alias is required."
                        }
                    }
                },
                name: {
                    validators: {
                        notEmpty: {
                            message: "name is required."
                        }
                    }
                },
                address: {
                    validators: {
                        notEmpty: {
                            message: "address is required."
                        }
                    }
                },
                phone: {
                    validators: {
                        notEmpty: {
                            message: "phoneNumber is required."
                        }
                    }
                },
                contact: {
                    validators: {
                        notEmpty: {
                            message: "contact person is required."
                        }
                    }
                },
                mobile: {
                    validators: {
                        notEmpty: {
                            message: "Mobile phone is required."
                        }
                    }.regexp({
                            regexp: "^(\\d{3,4}-?\\d{7,8}|(13|15|18)\\d{9})$",
                            onError: "Mobile type is incorrect,please check!"
                        })
                },
                telephone:{
                    validators: {
                        notEmpty: {
                            message: "Telephone is required."
                        }
                    }.regexp({
                            regexp: "^(\\d{3,4}-?\\d{7,8}|(13|15|18)\\d{9})$",
                            onError: "Telephone type is incorrect,please check!"
                        })
                }
            }
        });
    }


    return {
        formValidator: function (paramter) {
            return validator(paramter);
        }

    }
});