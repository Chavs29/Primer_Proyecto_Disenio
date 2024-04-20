function generarLlaveSecreta() {
    var randomArray = new Uint8Array(20);
    window.crypto.getRandomValues(randomArray);
    var base32 = new base32Encode(randomArray);
    return base32;
}

function obtenerCodigoGoogle(llaveSecreta, cuenta) {
    var empresa = "Jimaye";
    try {
        return "otpauth://totp/"
            + encodeURIComponent(empresa + ":" + cuenta).replace(/%2B/g, '%20')
            + "?secret=" + encodeURIComponent(llaveSecreta).replace(/%2B/g, '%20')
            + "&issuer=" + encodeURIComponent(empresa).replace(/%2B/g, '%20');
    } catch (e) {
        throw new Error(e);
    }
}

function crearQR(codigoDatos) {
    var qrCode = new QRCodeStyling({
        width: 500,
        height: 500,
        data: codigoDatos,
        imageOptions: {
            hideBackgroundDots: true,
            imageSize: 0.8
        }
    });

    qrCode.append(document.getElementById("contenedorQR"));
}

function obtenerCodigoMostrado(llaveSecreta) {
    var base32 = new base32Decode(llaveSecreta);
    var hexKey = new hexEncode(base32);
    return TOTP.getOTP(hexKey);
}
