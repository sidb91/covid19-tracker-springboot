jQuery(document).ready(function () {
    //alert('hello');
    $('#covidDataTable').DataTable({
        "pagingType": "full_numbers",
        "responsive": "true",
        //"pagingType": "simple", // "simple" option for 'Previous' and 'Next' buttons only
        "order": [[ 0, 'asc' ]]
      });
      $('.dataTables_length').addClass('bs-select');
});
