import { useNavigate, useParams } from "react-router-dom";
import { Header } from "../compoents/Header/Header";
import { useContext, useEffect } from "react";
import { TasksContext } from "../context/TasksContext";
import { Item, ListPage } from "../compoents/List/styles";
import { UserContext } from "../context/UserContext";
import { CaretLeft } from "phosphor-react";

export function ViewPage() {
    const { id } = useParams<{ id: string }>();
    const { viewTask, viewTaskData } = useContext(TasksContext);
    const navigate = useNavigate()

    useEffect(() => {
        if (!id) {
            console.error("ID não encontrado");
            return;
        }
        viewTask(id);
    }, [id, viewTask]);

    if (!viewTaskData) {
        return (
            <>
                <Header />
                <ListPage>
                    <p>Carregando tarefa...</p>
                </ListPage>
            </>
        );
    }
    const context = useContext(UserContext)
    const { currentUser, logout } = context;

    return (
        <div>
            <Header />
            <ListPage>
                <Item>
                    <button
                        onClick={async () => {
                            navigate(`/task`);
                        }}
                        aria-label={`Voltar a Tasks`}
                    >
                        <CaretLeft size={20} color="#a9a9a9" />
                    </button>
                    <p>
                        Task: {viewTaskData.name} - Usuário: {viewTaskData.userId}
                    </p>
                </Item>
                <Item style={{ height: "auto" }}>
                    <textarea
                        className="description"
                        readOnly
                        value={viewTaskData.description || ""}
                        rows={1}
                        onChange={() => { }}
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
                <Item>
                    <p>
                        Data Limite: {viewTaskData.limitDate?.toLocaleDateString("pt-BR") ?? "Não definida"}   -
                        Status: {viewTaskData.is_active? "Ativa" : "Concluída"}
                    </p>
                </Item>
            </ListPage>
        </div>
    );
}
