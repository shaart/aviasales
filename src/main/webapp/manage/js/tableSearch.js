function searchAtTable() {
  // Declare variables
  var input, filter, table, tr, td, i, j, curr_td;
  input = document.getElementById("seekFor");
  filter = input.value.toUpperCase();
  table = document.getElementById("dataTable");
  tr = table.getElementsByTagName("tr");

  // Loop through all table rows, and hide those who don't match the search query
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td");
    for (j = 0; j < td.length; j++) {
      curr_td = td[j];
      if (curr_td) {
        if (curr_td.innerHTML.toUpperCase().indexOf(filter.toUpperCase()) > -1) {
          tr[i].style.display = "";
          window.alert("found");
        } else {
          tr[i].style.display = "none";
          window.alert("not found");
        }
      }
    }
  }
}