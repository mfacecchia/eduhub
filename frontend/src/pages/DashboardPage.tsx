import ClassCard from "@/components/ClassCard";
import {
    Carousel,
    CarouselContent,
    CarouselItem,
} from "@/components/common/Carousel";
import Section from "@/components/common/Section";
import LessonCard from "@/components/LessonCard";
import { useAccountAuthContext } from "@/context/AuthContext";
import LoadingLayout from "@/layouts/LoadingLayout";
import getGreeting from "@/lib/greetingSelector";
import { ChevronRight } from "lucide-react";
import { useEffect } from "react";
import { Link, useNavigate } from "react-router";

const DashboardPage = () => {
    const { account, isLoading } = useAccountAuthContext();
    const greeting = getGreeting();
    const navigate = useNavigate();

    useEffect(() => {
        if (!account) navigate("/login");
    });

    return isLoading ? (
        <LoadingLayout />
    ) : (
        <>
            <section>
                <p className="large">{greeting ?? "Hello"},</p>
                <h2>
                    {account?.firstName} {account?.lastName}
                </h2>
            </section>
            <main className="mb-11">
                <Section>
                    <div className="flex items-center justify-between py-3">
                        <h4>Next Lesson</h4>
                        <Link
                            to="./"
                            className="flex items-center gap-1 text-muted-foreground"
                        >
                            <span className="small">See all</span>{" "}
                            <ChevronRight className="size-4" />
                        </Link>
                    </div>
                    <LessonCard
                        courseName="Java"
                        lessonDate="19/12/2024"
                        startsAt="14:00"
                        endsAt="18:00"
                        roomNo="R001"
                        linkToLesson="./"
                    />
                </Section>
                <Section>
                    <div className="py-3">
                        <h4>Courses</h4>
                    </div>
                    <Carousel
                        opts={{
                            align: "start",
                        }}
                    >
                        {/* TODO: Replace mock data with actual classes from backend */}
                        <CarouselContent className="-ml-2">
                            <CarouselItem className="basis-[45%] pl-2">
                                <ClassCard
                                    courseName="Java"
                                    linkToClass="/classes/2"
                                    teacherName="John Doe"
                                />
                            </CarouselItem>
                            <CarouselItem className="basis-[45%] pl-2">
                                <ClassCard
                                    courseName="React.JS"
                                    linkToClass="./"
                                    teacherName="John Doe"
                                    teacherAvatarUrl="https://picsum.photos/100"
                                />
                            </CarouselItem>
                            <CarouselItem className="basis-[45%] pl-2">
                                <ClassCard
                                    courseName="React.JS"
                                    linkToClass="./"
                                    teacherName="John Doe"
                                    teacherAvatarUrl="https://picsum.photos/100"
                                />
                            </CarouselItem>
                        </CarouselContent>
                    </Carousel>
                </Section>
            </main>
        </>
    );
};

export default DashboardPage;
