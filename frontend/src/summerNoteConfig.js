$(function () {
    $(document).ready(function() {
        $('#contents').summernote({
            height: 400,
            minHeight: null,
            maxHeight: null,
            callbacks: {
                onImageUpload : function(files) {
                    for (let i = 0; i < files.length; i++) {
                        // uploadFile(files[i], this);
                    }
                },
                onPaste: function (e) {
                    let clipboardData = e.originalEvent.clipboardData;
                    if (clipboardData && clipboardData.items && clipboardData.items.length) {
                        let item = clipboardData.items[0];
                        if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
                            e.preventDefault();
                        }
                    }
                }
            }
        })
    })
})