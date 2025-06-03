import { useParams } from "react-router-dom";
import { Header } from "../compoents/Header/Header";
import { useContext, useEffect } from "react";
import { TasksContext } from "../context/TasksContext";
import { Item, ListPage } from "../compoents/List/styles";


export function ViewPage() {
    const { id } = useParams<{ id: string }>()
    const { viewTask, viewTaskData } = useContext(TasksContext)

    useEffect(() => {
        if (!id) {
            console.error("ID não encontrado");
            return;
        }

        viewTask(id);
    }, [id]);

    console.log(viewTaskData)
    return (
        <div>
            <Header />
            <ListPage>
                <Item>
                    <p>Task: {viewTaskData?.name}   -  Usuário:{viewTaskData?.user_id}</p>
                </Item>
                <Item style={{height:"auto"}}>
                    <textarea
                        className="description"
                        readOnly
                        value={viewTaskData?.description || ""}
                        rows={1}
                        onChange={() => { }} // impede warnings, mesmo que não seja usado
                        style={{
                            height: "auto",
                            overflow: "hidden",
                            resize: "none",
                        }}
                        ref={(textarea) => {
                            if (textarea) {
                                textarea.style.height = "auto";
                                textarea.style.height = `${textarea.scrollHeight}px`;
                            }
                        }}
                    />
                </Item>
                <Item >
                    <p>{viewTaskData?.create_at}    -   {viewTaskData?.taskIsActive}</p>
                </Item>

            </ListPage>
        </div>
    )
}