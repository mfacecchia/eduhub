import { Avatar, AvatarFallback } from "@/components/common/Avatar";
import { Card, CardFooter, CardHeader } from "@/components/common/Card";
import { Header, HeaderLinkAction } from "@/components/common/Header";
import {
    Tabs,
    TabsContent,
    TabsList,
    TabsTrigger,
} from "@/components/common/Tabs";
import DocumentCard from "@/components/DocumentCard";
import LessonCard from "@/components/LessonCard";
import QuizCard from "@/components/QuizCard";
import { getAccountInitialsFullName } from "@/lib/accountInitials";
import { Users } from "lucide-react";

const ClassPage = () => {
    return (
        <>
            <Card className="bg-gradient-to-tr from-[#D4145A] to-[#FBB03B] text-primary-foreground flex flex-col justify-between shadow-none min-h-44">
                <CardHeader className="space-y-0">
                    <h2 className="leading-none">Java</h2>
                    <p className="large leading-none">
                        Software Development 23/24
                    </p>
                </CardHeader>
                <CardFooter className="justify-between">
                    <div className="bg-secondary rounded-full flex items-center gap-1 p-1 text-secondary-foreground">
                        <Avatar className="size-6">
                            <AvatarFallback className="bg-muted-foreground text-muted text-xs">
                                {getAccountInitialsFullName("John doe")}
                            </AvatarFallback>
                        </Avatar>
                        <span className="small">John Doe</span>
                    </div>
                    <div className="flex items-center gap-1">
                        <Users className="size-4" />
                        <span>11</span>
                    </div>
                </CardFooter>
            </Card>
            <section className="py-3">
                <Tabs defaultValue="lessons" className="mb-7">
                    <TabsList className="w-full grid grid-cols-3">
                        <TabsTrigger value="lessons">Lessons</TabsTrigger>
                        <TabsTrigger value="documents">Documents</TabsTrigger>
                        <TabsTrigger value="quizzes">Quizzes</TabsTrigger>
                    </TabsList>
                    <TabsContent value="lessons">
                        <div>
                            <Header>
                                <h4>Next Lessons</h4>
                                <HeaderLinkAction
                                    urlTo="./lessons"
                                    text="See all"
                                />
                            </Header>
                            <div className="w-full grid grid-cols-1 gap-3">
                                <LessonCard
                                    courseName="Java"
                                    lessonDate="19/12/2024"
                                    startsAt="14:00"
                                    endsAt="18:00"
                                    roomNo="R001"
                                    linkToLesson="./"
                                />
                                <LessonCard
                                    courseName="Java"
                                    lessonDate="19/12/2024"
                                    startsAt="14:00"
                                    endsAt="18:00"
                                    roomNo="R001"
                                    linkToLesson="./"
                                />
                            </div>
                        </div>
                    </TabsContent>
                    <TabsContent value="documents">
                        <div>
                            <Header>
                                <h4>Shared Documents</h4>
                                <HeaderLinkAction
                                    urlTo="./lessons"
                                    text="See all"
                                />
                            </Header>
                            <div className="w-full grid grid-cols-1 gap-3">
                                <DocumentCard
                                    docType="Java"
                                    size="50MB"
                                    fileName="JDBC Configuration"
                                    link="./"
                                    sharedDate="19/12/2024"
                                />
                                <DocumentCard
                                    docType="Link"
                                    size="0B"
                                    fileName="JDBC"
                                    link="https://github.com/mfacecchia/eduhub"
                                    sharedDate="18/12/2024"
                                />
                            </div>
                        </div>
                    </TabsContent>
                    <TabsContent value="quizzes">
                        <div>
                            <Header>
                                <h4>Due</h4>
                                <HeaderLinkAction
                                    urlTo="./quizzes?completed=false"
                                    text="See all"
                                />
                            </Header>
                            <div className="w-full grid grid-cols-1 gap-3">
                                <QuizCard
                                    title="December Exam"
                                    assignedDate="19/12/2024"
                                    linkToQuiz="./quiz"
                                    dueDate="19/12/2024"
                                />
                            </div>
                        </div>
                        <div>
                            <Header>
                                <h4>Completed</h4>
                                <HeaderLinkAction
                                    urlTo="./quizzes/?completed=true"
                                    text="See all"
                                />
                            </Header>
                            <div className="w-full grid grid-cols-1 gap-3">
                                <QuizCard
                                    title="December Exam"
                                    assignedDate="19/12/2024"
                                    linkToQuiz="./quiz"
                                    dueDate="19/12/2024"
                                />
                                <DocumentCard
                                    docType="Link"
                                    size="0B"
                                    fileName="JDBC"
                                    link="https://github.com/mfacecchia/eduhub"
                                    sharedDate="18/12/2024"
                                />
                            </div>
                        </div>
                    </TabsContent>
                </Tabs>
            </section>
        </>
    );
};

export default ClassPage;
