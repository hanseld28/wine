var Wine = Wine || {};

Wine.UploadFoto = (function() {
	
	function UploadFoto(idRelacionado) {
		this.uploadDrop = $('#upload-drop');
		this.containerFoto = $('.js-container-foto');
		this.header = $('input[name=_csrf_header').val();
		this.token = $('input[name=_csrf').val();
		this.data = new FormData($('#form-upload')[0]);
		this.idRelacionado = idRelacionado;
	}
	
	UploadFoto.prototype.iniciar = function() {
	    $.ajax({
	        url: `/fotos/${this.idRelacionado}`,
	        method: 'POST',
	        data: this.data,
	        cache: false,
	        contentType: false,
	        processData: false,
	        enctype: 'multipart/form-data',
	        beforeSend: adicionarCsrfToken.bind(this),
	        success: onUploadCompleto.bind(this)
	    });
	}
	
	function adicionarCsrfToken(xhr) {
		xhr.setRequestHeader(this.header, this.token);
	}
	
	function onUploadCompleto(foto) {
    	this.uploadDrop.addClass('hidden');
        this.containerFoto.prepend(`<img src='${foto.url}' class='img-responsive' width='200' height='200' style='margin:auto' />`);
    }
	
	return UploadFoto;
})();

$('#form-upload').on('submit', function (e) {
    e.preventDefault();
    
    var idVinho = this.dataset.idVinho;
    var uploadFoto = new Wine.UploadFoto(idVinho);
    
    uploadFoto.iniciar();
});
