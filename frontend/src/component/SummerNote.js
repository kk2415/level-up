import React, {Component, useState, useEffect, useContext, useLayoutEffect} from 'react';
import ReactSummernote from 'react-summernote';
import { sendMultiPart, send } from "../api/request"
import { BACKEND_URL } from "../api/backEndHost.js"
import $ from 'jquery' // summernote needs it
import {} from 'react-bootstrap'
import 'bootstrap' // ibid.
import 'react-summernote/dist/react-summernote.css'; // import styles
import 'react-summernote/lang/summernote-ru-RU'; // you can import any other locale
import {uploadFile} from '../api/FileService'
// Import bootstrap(v3 or v4) dependencies
import 'bootstrap/dist/css/bootstrap.css';

class RichTextEditor extends Component {
    constructor(props) {
        super(props);

        this.contents = props.contents
        this.setContents = props.setContents
    }

    onChange(content, $editable) {
    }

    onInit = (note) => {
        note.reset()
        note.summernote('pasteHTML', this.contents);
        this.setContents(note.summernote('code'))
    }

    onFocus = () => {
        this.setContents(this.wysiwygEditorRef.editor.summernote('code'))
    }

    onEnter = () => {
        this.setContents(this.wysiwygEditorRef.editor.summernote('code'))
    }

    onChangeCodeview = (content) => {
        console.log(1)
    }

    onImageUpload = (images, insertImage) => {
        for (let i = 0; i < images.length; i++) {
            let reader = new FileReader();
            reader.readAsDataURL(images[i])

            reader.onloadend = () => {
                const base64 = reader.result
                ReactSummernote.insertImage(base64);
            }
        }
    };

    render() {
        return (
            <ReactSummernote
                value="Default value"
                options={{
                    lang: 'ko-KR',
                    height: 350,
                    dialogsInBody: true,
                    toolbar: [
                        ['style', ['style']],
                        ['font', ['bold', 'underline', 'clear']],
                        ['fontname', ['fontname']],
                        ['para', ['ul', 'ol', 'paragraph']],
                        ['table', ['table']],
                        ['insert', ['link', 'picture', 'video']],
                        ['view', ['fullscreen', 'codeview']]
                    ]
                }}
                onChange={this.onChange}
                onInit={this.onInit}
                onFocus={this.onFocus}
                onEnter={this.onEnter}
                onImageUpload={this.onImageUpload}
                onChangeCodeview={this.onChangeCodeview}
                ref={el => this.wysiwygEditorRef = el}
            />
        );
    }
}

export default RichTextEditor;