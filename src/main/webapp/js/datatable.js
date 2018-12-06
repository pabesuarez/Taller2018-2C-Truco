$(document).ready( function () {
    $('#partidas').DataTable({
        "order": [[ 1, 'asc' ],[0, 'desc']]
    }
   );
    

    $('#partidasindex').DataTable({
        "searching": false,
        "ordering": false,
        "info": false,
        "bLengthChange": false
    }
   );
    
} );

