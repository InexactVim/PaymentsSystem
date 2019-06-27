function isNumber(evt) {
  evt = (evt) ? evt : window.event;
  var charCode = (evt.which) ? evt.which : evt.keyCode;

  return !((charCode > 31 && charCode < 48) || charCode > 57);
}

function checkSumField(evt) {
  evt = (evt) ? evt : window.event;
  var charCode = (evt.which) ? evt.which : evt.keyCode;

  if (charCode === 46) {
    return true;
  }

  return !((charCode > 31 && charCode < 48) || charCode > 57);
}
