import React, {useState, useEffect, useLayoutEffect} from 'react';
import ChannelService from '../../api/service/ChannelService'
import {MemberService} from '../../api/service/MemberService'
import {Container, FloatingLabel, Form, Row} from 'react-bootstrap'
import {uploadFile} from "../../api/UploadFile";
import $ from "jquery";

const CreateChannel = () => {
    const getChannelId = () => {
        let pathname = decodeURI($(window.location).attr('pathname'))

        return Number(pathname.substr(pathname.lastIndexOf('/') + 1))
    }

    const loadDescription = async (channelId) => {
        let result = await ChannelService.get(channelId)

        setDescription(result)
    }

    const handleChangeThumbnail = (event) => {
        setThumbnail(event.target.files[0])
    }

    const handleBackButton = () => {
        window.history.back();
    }

    const handleModifyButton = async () => {
        let thumbnailImageDir = description.thumbnailImage
        if (thumbnail !== null) {
            thumbnailImageDir = await MemberService.uploadProfile(thumbnail)
        }

        let channel = {
            name : $('#name').val(),
            limitedMemberNumber : $('#limitedMemberNumber').val(),
            description : $('#summernote').val(),
            category : $('#category').val(),
            thumbnailImage : thumbnailImageDir,
        }

        let result = await ChannelService.modify(channel, channelId);
        if (result) {
            alert('수정되었습니다.')
            window.location.href = '/channel/description/' + channelId
        }
    }

    const getUploadFiles = (htmlCode) => {
        let uploadFiles = []
        let offset = 0

        while (htmlCode.indexOf('img src', offset) !== -1) {
            let uploadFile = {}

            let imgTagStr = htmlCode.substr(htmlCode.indexOf('img src', offset))
            let firstIdx = imgTagStr.indexOf('"') + 1
            let lastIdx = imgTagStr.indexOf('"', firstIdx)

            let base64 = imgTagStr.substring(firstIdx, lastIdx)
            uploadFile.storeFileName = base64.substr(base64.indexOf(',') + 1)
            uploadFile.uploadFileName = base64

            uploadFiles.push(uploadFile)

            offset = htmlCode.indexOf('img src', offset) + 'img src'.length
        }
        return uploadFiles;
    }

    const [thumbnail, setThumbnail] = useState(null)
    const [channelId, setChannelId] = useState(getChannelId())
    const [description, setDescription] = useState(null)

    const uploadImage = (images, insertImage) => {
        for (let i = 0; i < images.length; i++) {
            uploadFile('/api/channel/descriptionFiles', 'POST', images[i])
                .then((data) => {
                })
                .catch((error) => {
                    console.log(error)
                })
            // uploadImage(images[i], insertImage);
        }
    }

    const showChannel = () => {
        if (description) {
            $('#name').val(description.name)
            $('#limitedMemberNumber').val(description.limitedMemberNumber)
            $('#thumbnailDescription').val(description.thumbnailDescription)
        }
    }

    const configSummernote = () => {
        $(document).ready(function() {
            $('#summernote').summernote({
                height: 400,
                minHeight: null,
                maxHeight: null,
                callbacks: {
                    onImageUpload : function(images) {
                        for (let i = 0; i < images.length; i++) {
                            let reader = new FileReader();
                            reader.readAsDataURL(images[i])

                            reader.onloadend = () => {
                                const base64 = reader.result
                                $('#summernote').summernote('insertImage', base64)
                            }
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
    }

    useEffect(() => {
        showChannel()
        configSummernote()
        if (description) {
            $('#summernote').val(description.description)
        }
    }, [description])

    useLayoutEffect(() => {
        setChannelId(getChannelId())
        loadDescription(channelId)
    }, [])

    return (
        <>
            <Container style={{width: "100%", fontFamily: "sans-serif"}}>
                {
                    description &&
                    <Row className='d-flex justify-content-center align-items-center'>
                        <Form id='form' style={{width: "70%"}}>
                            <FloatingLabel label="채널 정원" className="mb-3 " style={{width: "11%", display: 'inline-block', marginRight: 20}}>
                                <Form.Control type="number" id="limitedMemberNumber" name="limitedMemberNumber" placeholder="회원 제한 수"/>
                            </FloatingLabel>

                            <Form.Select className="mb-3 form-control" name="category" id="category" value={description.category}
                                         style={{width: "86%", display: 'inline-block'}}>
                                <option className="fs-4" selected value="NONE" placeholder="name@example.com">카테고리를 선택해주세요</option>
                                <option className="fs-4" value="STUDY">스터디</option>
                                <option className="fs-4" value="PROJECT">프로젝트</option>
                            </Form.Select>

                            <FloatingLabel label="스터디 이름" className="mb-3" style={{width: "100%"}}>
                                <Form.Control id="name" name="name" />
                            </FloatingLabel>

                            <Form.Group style={{width: "100%"}}>
                                <Form.Label>대표 사진</Form.Label>
                                <Form.Control onChange={handleChangeThumbnail} id='file' type='file' />
                            </Form.Group>

                            <Form.Group className="mb-3 mt-5" style={{width: "100%"}}>
                                <Form.Label className="fs-3 fw-bold">스터디 설명</Form.Label>
                                <textarea id='summernote' />
                            </Form.Group>

                            <div className="row mt-5">
                                <div className="col">
                                    <button onClick={handleModifyButton} className="w-100 btn btn-primary btn-lg" type="button"
                                            id="modifyButton">수정</button>
                                </div>
                                <div className="col">
                                    <button onClick={handleBackButton} className="w-100 btn btn-secondary btn-lg" type="button" id="cancel">뒤로가기</button>
                                </div>
                            </div>
                        </Form>
                    </Row>
                }
            </Container>
        </>
    );
};

export default CreateChannel;
