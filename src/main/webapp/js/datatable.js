$(document).ready( function () {
    $('#partidas').DataTable({
        "order": [[ 1, 'asc' ],[0, 'desc']]
    }
   );
} );