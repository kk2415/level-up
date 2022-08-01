import { sendMultiPart, send } from "./request"

export async function uploadFile(url, method, file) {
    let form = new FormData()
    let uploadImage = {}

    form.append('file', file)

    await sendMultiPart(url, method, form)
        .then((data) => {
            uploadImage.storeFileName = data.storeFileName
            uploadImage.uploadFileName = data.uploadFileName
        })
        .catch((error) => {
            console.log(error)
            uploadImage = null
        })

    return uploadImage
}

export async function uploadFileByBase64(url, method, file) {
    send(url, method, file)
        .then((data) => {console.log(data)})
        .catch((error) => {console.log(error)})
}