const form = document.getElementById('form');
const oDate = document.getElementById('orderDate');
const sDate = document.getElementById('shipmentDate');
const dDate = document.getElementById('deliveryDate');

function checkForm(form)
{
    return validateInputs();
}

const setError = (element, message) => {
	
	const inputControl = element.parentElement;
	const errorDisplay = inputControl.querySelector('.error');
	
	errorDisplay.innerText = message;
	inputControl.classList.add('error');
	inputControl.classList.remove('success');
};

const setSuccess = element => {
	const inputControl = element.parentElement;
	const errorDisplay = inputControl.querySelector('.error');
	
	errorDisplay.innerText = '';
	inputControl.classList.add('success');
	inputControl.classList.remove('error');
};

validateInputs = () => {

	var result = false;

	const orderD = Date.parse(oDate.value);
	const shipmentD = new Date(sDate.value);
	const deliveryD = new Date(dDate.value);
	
	if(shipmentD < orderD) {
		setError(shipmentDate, 'The shipment date is incorrect');
		result = false;
	}
	else {
		setSuccess(shipmentDate);
		result = true;
	}
	
	if(deliveryD < orderD || deliveryD < shipmentD) {
		setError(deliveryDate, 'The delivery date is incorrect');
		result = false;
	}
	else {
		setSuccess(deliveryDate);
		result = true;
	}

	if((orderD <= shipmentD) && (orderD <= deliveryD) && (shipmentD <= deliveryD)) {
	    result = true;
	}
	else {
	    result = false;
	}

	return result;
};