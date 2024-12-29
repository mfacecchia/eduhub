type TGreetings = {
    greeting: string;
    timeRange: number[];
};

const greetings: TGreetings[] = [
    {
        greeting: "Good Morning",
        timeRange: [5, 12],
    },
    {
        greeting: "Good Afternoon",
        timeRange: [12, 18],
    },
    {
        greeting: "Good Evening",
        timeRange: [18, 24],
    },
    {
        greeting: "Good Evening",
        timeRange: [0, 5],
    },
];

export default function getGreeting() {
    const time = new Date().getHours();
    return greetings.find((timeGreeting) => {
        return (
            time > timeGreeting.timeRange[0] &&
            time <= timeGreeting.timeRange[1]
        );
    })?.greeting;
}
