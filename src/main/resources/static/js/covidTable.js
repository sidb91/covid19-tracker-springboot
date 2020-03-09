jQuery(document).ready(function () {
    //alert('hello');
    $('#covidDataTable').DataTable({
        "pagingType": "simple", // "simple" option for 'Previous' and 'Next' buttons only
        "order": [[ 1, 'asc' ]]
      });
      $('.dataTables_length').addClass('bs-select');
});
