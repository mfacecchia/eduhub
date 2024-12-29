import { fetchAccount } from "@/api/accountApi";
import Section from "@/components/common/Section";
import LessonCard from "@/components/LessonCard";
import getGreeting from "@/lib/greetingSelector";
import { useQuery } from "@tanstack/react-query";
import { ChevronRight } from "lucide-react";
import { Link } from "react-router";

const DashboardPage = () => {
    const { data: accountData, isLoading: isAccountLoading } = useQuery({
        queryKey: ["account"],
        queryFn: fetchAccount,
    });
    const greeting = getGreeting();

    // TODO: Display an error in case of failed fetch
    return (
        <>
            <section>
                <p className="large">{greeting ?? "Hello"},</p>
                <h2>{isAccountLoading ? "Loading..." : accountData?.name}</h2>
            </section>
            <main>
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
                    <h4>Courses</h4>
                </Section>
            </main>
        </>
    );
};

export default DashboardPage;
