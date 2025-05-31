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
                    <p>{viewTaskData?.name}</p>
                    <p>{viewTaskData?.description}</p>
                    <p>{viewTaskData?.user_id}</p>
                    <p>{viewTaskData?.taskIsActive}</p>
                </Item>
            </ListPage>
        </div>
    )
}