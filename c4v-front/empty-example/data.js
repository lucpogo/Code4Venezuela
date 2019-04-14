function loadDataSources() {
  for (var key in querys.responseJSON) {
    if (querys.responseJSON.hasOwnProperty(key)) {
      console.log(querys.responseJSON[key]);
      $("#dataSource").append(
        '<option value="' +querys.responseJSON[key].query +'">#' +querys.responseJSON[key].query +'</option>'
      );
    }
  }
}
