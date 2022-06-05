import React, {useState, useEffect, useContext} from 'react';
import { AuthContext } from '../../App';
import ChannelService from '../../api/ChannelService'
import {Container, Col, Row, Form, Button, Card} from 'react-bootstrap'
import {uploadFile} from "../../api/FileService";
import RichTextEditor from '../../component/SummerNote'

const CreateChannel = () => {
    const context = useContext(AuthContext);
    const [thumbnail, setThumbnail] = useState(null)
    const [contents, setContents] = useState('채널 설명')

    const handleChangeThumbnail = (event) => {
        setThumbnail(event.target.files[0])
    }

    const handleCreateButton = async () => {
        let formData = new FormData(document.getElementById('form'));
        let thumbnailImageDir = await uploadFile('/api/channel/thumbnail', 'POST', thumbnail)
        // let uploadFiles = getUploadFiles(formData.get('summernote'));

        let channel = {
            memberEmail : context.member.email,
            name : formData.get('name'),
            limitedMemberNumber : formData.get('limitedMemberNumber'),
            description : contents,
            category : formData.get('category'),
            thumbnailDescription : formData.get('thumbnailDescription'),
            thumbnailImage : thumbnailImageDir,
        }
        console.log(channel)
        console.log(contents)

        await ChannelService.create(channel)
    }

    function getUploadFiles(htmlCode) {
        let uploadFiles = []
        let offset = 0

        while (htmlCode.indexOf('img src', offset) !== -1) {
            let uploadFile = {}

            let imgTagStr = htmlCode.substr(htmlCode.indexOf('img src', offset))
            let firstIdx = imgTagStr.indexOf('"') + 1
            let lastIdx = imgTagStr.indexOf('"', firstIdx)

            uploadFile.storeFileName = imgTagStr.substring(firstIdx, lastIdx)
            uploadFile.uploadFileName = 'image'

            uploadFiles.push(uploadFile)

            offset = htmlCode.indexOf('img src', offset) + 'img src'.length
        }
        return uploadFiles;
    }

    return (
        <>
            <Container>
                <Form id='form'>
                    <Form.Group className='mb-4'>
                        <Form.Label>카테고리</Form.Label>
                        <Form.Select aria-label="Default select example" name="category" id="category">
                            <option selected value="NONE">카테고리를 선택해주세요</option>
                            <option value="STUDY">스터디</option>
                            <option value="PROJECT">프로젝트</option>
                        </Form.Select>
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>이름</Form.Label>
                        <Form.Control id="name" name="name" />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>회원 제한 수</Form.Label>
                        <Form.Control id="limitedMemberNumber" name="limitedMemberNumber" />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>설명</Form.Label>
                        {/*<Form.Control id="summernote" name='summernote' as="textarea" placeholder="Leave a contents here" />*/}
                        <RichTextEditor setContents={setContents} contents={contents} />
                    </Form.Group>

                    <Form.Group className="mb-3">
                        <Form.Label>썸네일 인사말</Form.Label>
                        <Form.Control id="thumbnailDescription" name="thumbnailDescription" />
                    </Form.Group>

                    <Form.Group>
                        <Form.Label>대표 사진</Form.Label>
                        <Form.Control onChange={handleChangeThumbnail} id='file' type='file' />
                    </Form.Group>

                    <div className="row mt-5">
                        <div className="col">
                            <button onClick={handleCreateButton} className="w-100 btn btn-primary btn-lg" type="button"
                                    id="createButton">생성</button>
                        </div>
                        <div className="col">
                            <button className="w-100 btn btn-secondary btn-lg" type="button" id="cancel">뒤로가기</button>
                        </div>
                    </div>
                </Form>
            </Container>
        </>
    );
};

export default CreateChannel;
