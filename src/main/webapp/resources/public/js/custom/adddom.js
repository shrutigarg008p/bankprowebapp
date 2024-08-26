var filestyler = new buttontoinputFile();

$(document).ready(function() {
       
    $("#addDom").click(function() {
        var domElement = $('<div class="row list_add"><div class="col-md-5 col-sm-5 col-xs-5"> Pan Card</div><div class="col-md-5 col-sm-5 col-xs-5"> 015245.jpg</div><div class="col-md-2 col-sm-2 col-xs-2"><button type="button" class="removebutton" title="Remove this row">X</button></div></div>');
        $(this).after(domElement);
    });

    $("#addDom3").click(function() {
        var domElement = $('<div class="row list_add"><div class="col-md-5 col-sm-5 col-xs-5"> Pan Card</div><div class="col-md-5 col-sm-5 col-xs-5"> 015245.jpg</div><div class="col-md-2 col-sm-2 col-xs-2"><button type="button" class="removebutton" title="Remove this row">X</button></div></div>');
        $(this).after(domElement);
    });

    $("#addDom2").click(function() {
    	
        var domElement = '<div class="row list_add"><div class="col-md-3 col-sm-3 col-xs-3"> Pan Card</div> <div class="col-md-3 col-sm-3 col-xs-3"> 015245.jpg</div><div class="col-md-4 col-sm-4 col-xs-4"><span class="text-danger"> Inactive</span></div><div class="col-md-2 text-right"><button type="button" class="removebutton" title="Remove this row">X</button></div></div>';
        $('#dataAdd').prepend(domElement);
        // $(this).after(domElement);
    });
    
});



$(document).on('click', 'button.removebutton', function () {
     alert("Are you sure you want to delete this documents");
     $(this).closest('.list_add').remove();
     return false;
});
