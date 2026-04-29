
function ComentarioInput({ value, onChange }) {
    return (
        <div>
            <label >Comentario</label>
            <textarea 
                name = "comentario"
                value={value}
                onChange={onChange}
                placeholder="Comentario opcional..."
            />
        </div>
    )

}
export default ComentarioInput;