const form = document.getElementById('form');
const oDate = document.getElementById('orderDate');
const dDate = document.getElementById('deliveryDate');
const sDate = document.getElementById('shipmentDate');

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
	const deliveryD = new Date(dDate.value);
	const shipmentD = new Date(sDate.value);
	
	if(deliveryD < orderD) {
		setError(deliveryDate, 'The delivery date is incorrect');
		result = false;
	}
	else {
		setSuccess(deliveryDate);
		result = true;
	}
	
	if(shipmentD < orderD || shipmentD < deliveryD) {
		setError(shipmentDate, 'The shipment date is incorrect');
		result = false;
	}
	else {
		setSuccess(shipmentDate);
		result = true;
	}

	return result;
};